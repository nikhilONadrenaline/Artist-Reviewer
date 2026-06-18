import React, { useEffect, useState } from 'react'
import { artistProjects } from '../../api/authApi'
import ProjectCard from '../../components/ProjectCard'

function Projects() {
  const [projects, setProjects]=useState([])

  const user=JSON.parse(localStorage.getItem("user"))

  const fetchProject=async ()=>{
    try {
      const response= await artistProjects(user.artistId);

      setProjects(response.data)
    } catch (error) {
      console.log(error);
    }
  }
  useEffect(() => {fetchProject()},[])

  return (
    <>
    <h3>Here are your projects🙂</h3>
    <br />

    <div className="max-w-7xl mx-auto px-6 py-8">
  <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
    {projects.map((project) => (
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
</div>
    </>
  )
}

export default Projects