import { useState } from "react";
import { signupReviewer } from "../../api/authApi";

function ReviewerSignupForm() {

    const [reviewer, setReviewer] = useState({
        name: "",
        expertise: "",
        upiId: "",
        email: "",
        password: "",
        role: "REVIEWER"
    });

    const handleChange = (e) => {
        setReviewer({
            ...reviewer,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const payload = {
            ...reviewer,
            expertise: reviewer.expertise
                .split(",")
                .map(item => item.trim())
        };

        try {

            const response =
                await signupReviewer(payload);

            console.log(response.data);

        } catch (err) {
            console.log(err);
        }
    };

    return (
        <form
            onSubmit={handleSubmit}
            className="flex flex-col gap-4"
        >

            <input
                name="name"
                placeholder="Name"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                name="expertise"
                placeholder="Expertise (comma separated)"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                name="upiId"
                placeholder="UPI ID"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                type="email"
                name="email"
                placeholder="Email"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                type="password"
                name="password"
                placeholder="Password"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <button
                className="bg-green-600 text-white p-2 rounded"
            >
                Signup Reviewer
            </button>

        </form>
    );
}

export default ReviewerSignupForm;