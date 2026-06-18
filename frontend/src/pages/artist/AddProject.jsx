import React, { useState } from 'react'
import { postProject } from '../../api/authApi'

function AddProject() {

  const user=JSON.parse(localStorage.getItem("user"))

  const [projectDto, setProjectDto]=useState({
    name:"Project",
    levelMin: 1,
    levelMax: 50,
    genre:[],
    payPerReview:1,
    totalReviewsNeeded:1,
    description:""
  })

  const addProject=async ()=>{
    try {
      const response=await postProject(user.artistId, projectDto)
      console.log(response.data);
      
      
    } catch (error) {
      console.log(error);
    }
  }

  const genres = [
  "Digital Art",
  "Illustration",
  "Concept Art",
  "Animation",
  "Photography",
  "Music",
  "Writing",
  "Video Editing",
  "Football",
];

  return (
    <>
    <br />
    <form onSubmit={(e)=>{
      e.preventDefault()
      addProject()
    }}
    className="max-w-3xl mx-auto bg-white p-8 rounded-2xl shadow-lg space-y-6">

    <h2 className="text-3xl font-bold text-gray-800">
      Create New Project
    </h2>

    {/* Project Name */}
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-2">
        Project Name
      </label>
      <input
        type="text"
        value={projectDto.name}
        onChange={(e) =>
          setProjectDto({ ...projectDto, name: e.target.value })
        }
        className="w-full border rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        placeholder="Enter project name"
      />
    </div>

    {/* Levels */}
    <div className="grid md:grid-cols-2 gap-4">
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Minimum Level
        </label>
        <input
          type="number"
          min="1"
          value={projectDto.levelMin}
          onChange={(e) =>
            setProjectDto({
              ...projectDto,
              levelMin: Number(e.target.value),
            })
          }
          className="w-full border rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Maximum Level
        </label>
        <input
          type="number"
          min="1"
          value={projectDto.levelMax}
          onChange={(e) =>
            setProjectDto({
              ...projectDto,
              levelMax: Number(e.target.value),
            })
          }
          className="w-full border rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>
    </div>

    {/* Genre */}
    <div>
  <label className="block text-sm font-medium text-gray-700 mb-3">
    Genres
  </label>

  <div className="flex flex-wrap gap-3">
    {genres.map((genre) => (
      <button
        type="button"
        key={genre}
        onClick={() => {
          if (projectDto.genre.includes(genre)) {
            setProjectDto({
              ...projectDto,
              genre: projectDto.genre.filter((g) => g !== genre),
            });
          } else {
            setProjectDto({
              ...projectDto,
              genre: [...projectDto.genre, genre],
            });
          }
        }}
        className={`px-4 py-2 rounded-full border transition-all duration-200
          ${
            projectDto.genre.includes(genre)
              ? "bg-indigo-600 text-white border-indigo-600"
              : "bg-white text-gray-700 border-gray-300 hover:border-indigo-500 hover:text-indigo-600"
          }`}
      >
        {genre}
      </button>
    ))}
  </div>
</div>

    {/* Payments */}
    <div className="grid md:grid-cols-2 gap-4">
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Pay Per Review (₹)
        </label>
        <input
          type="number"
          min="1"
          value={projectDto.payPerReview}
          onChange={(e) =>
            setProjectDto({
              ...projectDto,
              payPerReview: Number(e.target.value),
            })
          }
          className="w-full border rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Reviews Needed
        </label>
        <input
          type="number"
          min="1"
          value={projectDto.totalReviewsNeeded}
          onChange={(e) =>
            setProjectDto({
              ...projectDto,
              totalReviewsNeeded: Number(e.target.value),
            })
          }
          className="w-full border rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>
    </div>

    {/* Description */}
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-2">
        Description
      </label>

      <textarea
        rows="6"
        value={projectDto.description}
        onChange={(e) =>
          setProjectDto({
            ...projectDto,
            description: e.target.value,
          })
        }
        className="w-full border rounded-lg px-4 py-3 resize-none focus:outline-none focus:ring-2 focus:ring-indigo-500"
        placeholder="Tell reviewers about your project..."
      />
    </div>

    {/* Submit */}
    <button
      type="submit"
      className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-3 rounded-lg transition"
    >
      Create Project
    </button>
  </form>
  <br />
    </>
  )
}

export default AddProject