import React from 'react'
import { Outlet } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'


function ArtistLayout() {

  return (
    <>
    <Header/>
    <Outlet/>
    <Footer/>
    </>
  )
}

export default ArtistLayout