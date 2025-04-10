import { useEffect, useState } from "react"
import { IRoom } from "../../../interfaces/IRoom"
import axios from "axios"
import { useParams } from "react-router-dom"
import { ToggleButton, Typography } from "@mui/material"
import './Rooms.css'
import { AttachMoney, Bathroom, Bed, CheckBox, Delete, Edit } from "@mui/icons-material"



function Rooms() {

  const [rooms, setRooms] = useState<IRoom[]>([])
  const {hotelId} = useParams<{hotelId: string}>()



  useEffect(() =>{
    let getRooms = async () =>{
        try{
            let res = await axios.get<IRoom[]>(`http://localhost:8080/hotels/${hotelId}/rooms` , {withCredentials: true})
            setRooms(res.data)
        }
        catch(error){
            console.error("Could not retrieve rooms", error)
        }
    }


    getRooms()
    
  }, [])

  

  

  return (
    <div style={{width:"50%", margin:"auto"}}>
        <Typography variant="h1" fontSize={"28px"} marginTop={2}> Room Managment </Typography>
        <Typography fontSize={"18px"} marginTop={3}> Add, delete or edit your hotel rooms </Typography>

        <div className="room-container">
          <div className="room-item">
            <ul>
              <li>Type: Suite</li>
              <li>4 <Bed sx={{verticalAlign:"middle"}}/></li>
              <li>3 <Bathroom sx={{verticalAlign:"middle"}}/></li>
              <li><AttachMoney sx={{verticalAlign:"middle"}}/>699.99</li>
              <li>
                Available: <CheckBox sx={{verticalAlign:"middle"}}/>
              </li>
            </ul>

            <div className="icons">
              <Edit/>
              <Delete/>
            </div>
            
          
          </div>
        </div>

        


    </div>
  )
}

export default Rooms