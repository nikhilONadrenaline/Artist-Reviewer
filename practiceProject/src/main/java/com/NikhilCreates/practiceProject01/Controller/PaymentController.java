package com.NikhilCreates.practiceProject01.Controller;

import com.NikhilCreates.practiceProject01.Entity.PaymentVerificationRequest;
import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import com.NikhilCreates.practiceProject01.Service.ArtistService;
import com.NikhilCreates.practiceProject01.Service.ProjectService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String secret;


    @PostMapping("/create-order/{projectId}")
    public ResponseEntity<?> createOrder(@PathVariable String projectId) throws Exception {

        Project project=projectService.selectProject(projectId).orElseThrow(
                ()->new RuntimeException("No project found")
        );

        if(project.getStatus()!=ProjectStatus.MONEYNOTPAID) return ResponseEntity.badRequest().body("Already payed for this project");

        int amount=project.getTotalBudget();
        JSONObject options = new JSONObject();

        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());
        JSONObject notes = new JSONObject();
        notes.put("projectId", projectId);
        options.put("notes", notes);

        Order order = razorpayClient.orders.create(options);

        return ResponseEntity.ok(order.toString());
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody PaymentVerificationRequest request) throws Exception
    {
        JSONObject options = new JSONObject();

        options.put("razorpay_order_id",
                request.getRazorpayOrderId());

        options.put("razorpay_payment_id",
                request.getRazorpayPaymentId());

        options.put("razorpay_signature",
                request.getRazorpaySignature());

        boolean verified =
                Utils.verifyPaymentSignature(options, secret);

        if (!verified) {
            return ResponseEntity.badRequest()
                    .body("Verification Failed");
        }

        Order order =razorpayClient.orders.fetch(request.getRazorpayOrderId());

        JSONObject notes = (JSONObject) order.get("notes");
        String projectId = notes.get("projectId").toString();

        artistService.updateStatus(projectId);

        return ResponseEntity.ok("Payment Verified");
    }
}
