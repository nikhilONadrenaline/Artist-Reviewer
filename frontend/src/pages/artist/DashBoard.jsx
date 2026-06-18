import React, { useContext, useState } from 'react'
import UserContext from '../../context/UserContext'
import Profile from '../../components/Profile'
import { FaEdit } from "react-icons/fa";
import EditProfileModal from "../../components/EditProfileModal"
import { updateProfile } from '../../api/artistAPI';

function DashBoard() {

  const {user, setUser}=useContext(UserContext)

  const [artistDetail, setArtistDetail]=useState(user);
  const [edit,setEdit]=useState(false)

  const handleEdit=async (e)=>{
    
    e.preventDefault()
    const updatedUser={...user, name: artistDetail.name, genre:artistDetail.genre, emailId: artistDetail.emailId, upiId: artistDetail.upiId }
    try {
      await updateProfile(updatedUser); 

      setUser(updatedUser);             
      setEdit(false);  
    } 
    catch (error) {
      console.error(error);
      alert("Profile update failed");
    }
  }

  return (
    <>
    <Profile
    artist={user}/>

    <div className="flex justify-center mt-6">
  <button
    onClick={() => {
      setArtistDetail(user);
      setEdit(true);
    }}
    className="flex items-center gap-2 px-5 py-3 bg-indigo-600 text-white font-medium rounded-xl shadow-md hover:bg-indigo-700 hover:shadow-lg transition-all duration-200"
  >
    <FaEdit />
    Edit Profile
    </button>
    </div>
    <br />

    {edit && (
        <EditProfileModal
          artistDetail={artistDetail}
          setArtistDetail={setArtistDetail}
          handleEdit={handleEdit}
          onClose={() => setEdit(false)}
        />
      )}
    </>
  )
}

export default DashBoard