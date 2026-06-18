import React, { useEffect, useState } from 'react'
import {useNavigate, useParams} from 'react-router-dom'
import { selectProject, deleteProject, createOrder, verify } from '../../api/artistAPI';
import { useLoaderData } from "react-router-dom";


function ProjectDetail() {

  const {id}=useParams();
  const project = useLoaderData();


  
  const navigate=useNavigate();
  
  
  
  // const [project, setProject]=useState(null);
  // useEffect(()=>{
  //   const fetchProject=async()=>{
  //     try {
  //       const response=await selectProject(id)
  //       setProject(response.data)
  //     } catch (error) {
  //       console.log("Can't load the project");
  //     }
  //   }

  //   fetchProject()
  // },[id])

  const startPayment = async () => {
    try {
      const response = await createOrder(id);

      const data = response.data;

      const options = {
        key: "YOUR_RAZORPAY_KEY_ID",

        amount: data.amount,

        currency: data.currency,

        order_id: data.id,

        name: "Nikhil Creates",

        description: "Project Payment",

        handler: async function (response) {
          await verify({
            razorpayOrderId: response.razorpay_order_id,

            razorpayPaymentId: response.razorpay_payment_id,

            razorpaySignature: response.razorpay_signature,
          });

          alert("Payment Successful");
        },
      };

      const rzp = new window.Razorpay(options);

      rzp.open();
    } catch (error) {
      console.error(error);
      alert("Payment Failed");
    }
  };


  if(project==null) return (
    <>
    <div>Loading.....</div>
    </>
  )
  
  return (
    <>
    <div className="min-h-screen bg-gray-100 py-10 px-4">

    <div className="max-w-6xl mx-auto bg-white rounded-3xl shadow-xl overflow-hidden">

      

    {/* Hero Section */}
    <div className="relative h-96">
      <img
        src={project.imageUrl}
        alt={project.name}
        className="w-full h-full object-cover"
      />

      <div className="absolute inset-0 bg-linear-to-t from-black/80 via-black/30 to-transparent" />

      <div className="absolute bottom-8 left-8 text-white">
        <h1 className="text-5xl font-bold mb-3">
          {project.name}
        </h1>

        <div className="flex flex-wrap gap-3">
          {project.genre.map((g) => (
            <span
              key={g}
              className="px-4 py-1 bg-white/20 backdrop-blur-sm rounded-full text-sm"
            >
              {g}
            </span>
          ))}
        </div>
      </div>
    </div>

    {/* Content */}
    <div className="p-8">

      {/* Stats Grid */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-10">

        <div className="bg-indigo-50 rounded-2xl p-5">
          <p className="text-sm text-gray-500">Rating</p>
          <h3 className="text-3xl font-bold text-indigo-600">
            ⭐ {project.rating}
          </h3>
        </div>

        <div className="bg-green-50 rounded-2xl p-5">
          <p className="text-sm text-gray-500">Pay / Review</p>
          <h3 className="text-3xl font-bold text-green-600">
            ₹{project.payPerReview}
          </h3>
        </div>

        <div className="bg-purple-50 rounded-2xl p-5">
          <p className="text-sm text-gray-500">Budget Left</p>
          <h3 className="text-3xl font-bold text-purple-600">
            ₹{project.remainingBudget}
          </h3>
        </div>

        <div className="bg-orange-50 rounded-2xl p-5">
          <p className="text-sm text-gray-500">Status</p>
          <h3 className="text-xl font-bold text-orange-600">
            {project.status}
          </h3>
        </div>

      </div>

      {/* Description */}
      <div className="mb-10">
        <h2 className="text-2xl font-bold mb-4">
          Description
        </h2>

        <p className="text-gray-900 leading-relaxed">
          {project.description}
        </p>
      </div>

      {/* Details */}
      <div className="grid md:grid-cols-2 gap-8">

        <div className="bg-gray-50 rounded-2xl p-6">
          <h3 className="text-xl font-bold mb-5">
            Review Requirements
          </h3>

          <div className="space-y-4">
            <div className="flex justify-between">
              <span>Minimum Level</span>
              <span className="font-semibold">{project.levelMin}</span>
            </div>

            <div className="flex justify-between">
              <span>Maximum Level</span>
              <span className="font-semibold">{project.levelMax}</span>
            </div>

            <div className="flex justify-between">
              <span>Reviews Needed</span>
              <span className="font-semibold">
                {project.totalReviewsNeeded}
              </span>
            </div>

            <div className="flex justify-between">
              <span>Reviews Received</span>
              <span className="font-semibold">
                {project.totalReviewsReceived}
              </span>
            </div>
          </div>
        </div>

        <div className="bg-gray-50 rounded-2xl p-6">
          <h3 className="text-xl font-bold mb-5">
            Financial Details
          </h3>

          <div className="space-y-4">
            <div className="flex justify-between">
              <span>Total Budget</span>
              <span className="font-semibold">
                ₹{project.totalBudget}
              </span>
            </div>

            <div className="flex justify-between">
              <span>Remaining Budget</span>
              <span className="font-semibold">
                ₹{project.remainingBudget}
              </span>
            </div>

            <div className="flex justify-between">
              <span>Created On</span>
              <span className="font-semibold">
                {new Date(project.createdAt).toLocaleDateString()}
              </span>
            </div>
          </div>
        </div>

      </div>

      {/* Progress */}
      <div className="mt-10">
        <div className="flex justify-between mb-2">
          <span className="font-medium">
            Review Progress
          </span>

          <span>
            {project.totalReviewsReceived}/
            {project.totalReviewsNeeded}
          </span>
        </div>

        <div className="w-full bg-gray-200 rounded-full h-4">
          <div
            className="h-4 rounded-full bg-linear-to-r from-indigo-500 to-purple-500"
            style={{
              width: `${
                (project.totalReviewsReceived /
                  project.totalReviewsNeeded) *
                100
              }%`,
            }}
          />
        </div>
      </div>

      </div>
      <div className="flex justify-center">
      <button 
        className=' px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition'
        onClick={async(e)=>{
        e.preventDefault();
        try {
          await deleteProject(id)
          navigate("/projects")
        } catch (error) {
          console.log("Couldnt delete the project");
        }
      }} >  Delete</button>

      <button 
        className=' px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition'
        onClick={startPayment} >  Activate Now</button>
      </div>
      <br />
      </div>
    </div>
    
    </>
  )
}

export default ProjectDetail