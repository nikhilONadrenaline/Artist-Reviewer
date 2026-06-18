import React, { useEffect } from 'react'
import UserContext from './UserContext'
import { useState } from 'react'

const UserContextProvider=({children})=> {
    const [user, setUser]=useState(JSON.parse(localStorage.getItem("user")) || null)

    useEffect(()=>{
        if(user){
            localStorage.setItem("user",JSON.stringify(user) )
        }
    },[user])

    return (
        <UserContext.Provider value={{user, setUser}}>
            {children}
        </UserContext.Provider>
    )
}

export default UserContextProvider