import React, { useEffect, useState } from 'react'
import { homeProjects } from '../../api/authApi'
import ProjectCard from '../../components/ProjectCard'

function Home() {
  const user=JSON.parse(localStorage.getItem("user"))

  const [filter, setFilter]=useState({
    genre:user.genre,
    minLevel: 1,
    maxLevel: 50,
    status: "OPEN"
  })

  const [projects, setProjects]=useState([])


  useEffect( ()=> {fetchProjects()}, [filter])

  const [selectedFilter, setSelectedFilter] = useState({
    genre: user.genre,
    minLevel: 1,
    maxLevel: 50,
    status: "OPEN"
  });


  const fetchProjects=async ()=> {
    try {
        const response =await homeProjects(
          filter.genre, filter.minLevel, filter.maxLevel, filter.status
        );

        setProjects(response.data)
        console.log(response.data)
        
    }
    catch (error) {      
      console.log("Couldnt Load projects");
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
    "Football"
  ];

  return (
    <>
    <h1>Let's scroll into some projects you may like!!!</h1>

    <div className="bg-white shadow-md rounded-lg p-5 mb-6">

  <h2 className="text-xl font-semibold mb-4">
    Filters
  </h2>

      {/* Genres */}

      <div className="mb-6">
    <h3 className="font-medium text-gray-700 mb-3">
      Genres
    </h3>

    <div className="flex flex-wrap gap-3">
      {genres.map((g) => (
        <button
          type="button"
          key={g}
          onClick={() => {
            if (selectedFilter.genre.includes(g)) {
              setSelectedFilter({
                ...selectedFilter,
                genre: selectedFilter.genre.filter(
                  (genre) => genre !== g
                ),
              });
            } else {
              setSelectedFilter({
                ...selectedFilter,
                genre: [...selectedFilter.genre, g],
              });
            }
          }}
          className={`px-4 py-2 rounded-full border transition-all duration-200
            ${
              selectedFilter.genre.includes(g)
                ? "bg-indigo-600 text-white border-indigo-600"
                : "bg-white text-gray-700 border-gray-300 hover:border-indigo-500 hover:text-indigo-600"
            }`}
        >
          {selectedFilter.genre.includes(g) ? "✓ " : ""}
          {g}
        </button>
      ))}
    </div>
      </div>

      {/* Levels */}

      <div className="flex gap-4 mb-4">

        <div>
          <label className="block">
            Min Level
          </label>

          <input
            type="number"
            min="1"
            max="50"
            value={selectedFilter.minLevel}
            onChange={(e) =>
              setSelectedFilter({
                ...selectedFilter,
                minLevel: Number(e.target.value)
              })
            }
            className="border rounded p-2"
          />
        </div>

        <div>
          <label className="block">
            Max Level
          </label>

          <input
            type="number"
            min="1"
            max="50"
            value={selectedFilter.maxLevel}
            onChange={(e) =>
              setSelectedFilter({
                ...selectedFilter,
                maxLevel: Number(e.target.value)
              })
            }
            className="border rounded p-2"
          />
        </div>

      </div>

      {/* Status */}

      <div className="mb-4">
        <label className="block mb-1">
          Status
        </label>

        <select
          value={selectedFilter.status}
          onChange={(e) =>
            setSelectedFilter({
              ...selectedFilter,
              status: e.target.value
            })
          }
          className="border rounded p-2"
        >
          <option value="OPEN">OPEN</option>
          <option value="COMPLETED">COMPLETED</option>
          <option value="CLOSED">CLOSED</option>
        </select>
      </div>

      {/* Search Button */}

      <button
        onClick={() => setFilter(selectedFilter)}
        className="bg-blue-600 text-white px-4 py-2 rounded"
      >
        Apply
      </button>

    </div>

    {/* Projects Mapped----CARDS creation  */}

    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

      {projects.map(project => (
        <ProjectCard
          key={project.id}
          id={project.id}
          projectName={project.name}
          minLevel={project.levelMin}
          maxLevel={project.levelMax}
          imageLink={project.imageUrl}
        />
      ))}
    </div>
    <br />
    </>
  )
}

export default Home
