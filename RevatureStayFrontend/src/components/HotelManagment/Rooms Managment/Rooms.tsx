import { ChangeEvent, JSX, use, useEffect, useState } from "react"

import axios from "axios"
import { useNavigate, useParams } from "react-router-dom"
import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Fab, IconButton, MenuItem, TextField, Tooltip, Typography } from "@mui/material"
import './Rooms.css'
import iRoom from "../../../interfaces/iRoom"
import React from "react"
import { Bed, Bathroom, AttachMoney, CheckBox, Edit, Delete, Add, ArrowBack } from "@mui/icons-material"



function Rooms() {

  const [rooms, setRooms] = useState<iRoom[]>([])
  const {hotelId} = useParams<{hotelId: string}>()
  const [openUpdate, setOpenUpdate] = useState(false);
  const [openAdd, setOpenAdd] = useState(false);
  const [currentRoom, setCurrentRoom] = useState<iRoom>()


  //Variable of a room
  const [roomType, setRoomType] = useState(currentRoom?.type)
  const [beds, setBeds] = useState(currentRoom?.beds)
  const [baths, setBaths] = useState(currentRoom?.baths)
  const [price, setPrice] = useState(currentRoom?.price)

  const [shouldUpdate, setShouldUpdate] = useState(false)

  const navigate = useNavigate()

  //Function to get all rooms
  useEffect(() =>{
    let getRooms = async () =>{
        try{
            let res = await axios.get<iRoom[]>(`http://localhost:8080/hotels/${hotelId}/rooms` , {withCredentials: true})
            setRooms(res.data)
        }
        catch(error){
            console.error("Could not retrieve rooms", error)
        }
    }
    getRooms()
  }, [shouldUpdate])

  //Function to manage edit room details
  let editRoomDetails = ( roomId: any) => {
    setOpenUpdate(true);
    setCurrentRoom(rooms.find((room) => room.roomId === roomId))
  }

  //Function to manage add new room details
  let addNewRoom = ( ) => {
    setOpenAdd(true);
  }

  //Function to delete room
  let deleteRoom = async (roomId: any) =>{
    setCurrentRoom(rooms.find((room) => room.roomId === roomId))
    
    try{
      let res = await axios.delete(`http://localhost:8080/hotels/${hotelId}/rooms/${currentRoom?.roomId}`, {withCredentials: true})
      console.log(res)
      
      setShouldUpdate(!shouldUpdate)
    } catch(error){
        console.error("Could not delete the room ", error)
    }
  }


  const handleCloseUpdate = () => {
    setOpenUpdate(false);
  };
  const handleCloseAdd = () => {
    setOpenAdd(false);
  };
  

  

  return (
    <div style={{width:"50%", margin:"auto", position:"relative"}}>
        <Button color="secondary" variant="contained" style={{position:"absolute", top:10, right: 10}} onClick={() => navigate("/my-hotels")} > <ArrowBack/> Back</Button>
        <Typography variant="h1" fontSize={"28px"} marginTop={2}> Room Managment </Typography>
        <Typography fontSize={"18px"} marginTop={3}> Add, delete or edit your hotel rooms </Typography>

        <div className="room-container">

          {
            rooms.map((room => {
              return (
                <div className="room-item" key={room.roomId}>
                  <ul className="room-item-content">
                    <li>Type: {room.type}</li>
                    <li>{room.beds} <Bed sx={{verticalAlign:"middle"}}/></li>
                    <li>{room.baths} <Bathroom sx={{verticalAlign:"middle"}}/></li>
                    <li><AttachMoney sx={{verticalAlign:"middle"}}/>{room.price}</li>
                    <li>
                      Available: <CheckBox sx={{verticalAlign:"middle"}}/>
                    </li>
                  </ul>

                  <div className="icons">
                  <Tooltip title="Edit Properties">
                      <IconButton onClick={() => editRoomDetails(room.roomId)}>
                        <Edit sx={{color:"white"}} />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete">
                      <IconButton onClick={() => deleteRoom(room.roomId) }>
                        <Delete sx={{color:"white"}} />
                      </IconButton>
                    </Tooltip>
                  </div>
                </div>
                
              )
            }))
          }
          <Fab color="primary" aria-label="add" sx={{marginTop: "15px"}} onClick={() =>{addNewRoom()}}>
            <Add/>
          </Fab>
        </div>

        {/* Start Dialog To Update Room*/}
        <Dialog
          open={openUpdate}
          onClose={handleCloseUpdate}
          slotProps={{
            paper: {
              component: 'form',
              onSubmit: async (event: React.FormEvent<HTMLFormElement>) => {
                event.preventDefault();
                
                //Send the request to update the room
                try{
                  let res = await axios.put<{
                    type: string,
                    beds: number,
                    baths: number,
                    price: number
                  }>(`http://localhost:8080/hotels/${hotelId}/rooms/${currentRoom?.roomId}`,
                    {
                      type: roomType,
                      beds: beds,
                      baths: baths,
                      price: price
                    },
                    {withCredentials: true}
                  )

                  console.log(res)
                  setShouldUpdate(!shouldUpdate)

                } catch (error){
                  console.error("Cannot update the room", error)
                }
                
                handleCloseUpdate();
              },
            },
          }}
        >
            <DialogTitle>Edit Room Properties</DialogTitle>
            <DialogContent sx={{alignItems:"center"}}>
              <DialogContentText>
                To edit the room details, please enter the new data
              </DialogContentText>

              <TextField
                id="roomType-input"
                select
                label="Room Type"
                defaultValue={currentRoom?.type}
                helperText="Please select the room type"
                size="small"
                sx={{
                  width: "50%",
                  marginTop: "15px"
                }}
                onChange={(e: ChangeEvent<HTMLInputElement>) => { setRoomType(e.target.value) }}
              >     
              <MenuItem key={"SINGLE"} value={"SINGLE"} >SINGLE</MenuItem>
              <MenuItem key={"DOUBLE"} value={"DOUBLE"} >DOUBLE</MenuItem>
              <MenuItem key={"TRIPLE"} value={"TRIPLE"} >TRIPLE</MenuItem>
              <MenuItem key={"SUITE"} value={"SUITE"} >SUITE</MenuItem>

              </TextField>
              <Box sx={{ display: 'flex', alignItems: 'flex-end'}}>
                <Bed sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField 
                  id="beds-input" 
                  type="number" 
                  label="Total Beds" 
                  variant="standard" 
                  defaultValue={currentRoom?.beds}
                  onChange={(e: ChangeEvent<HTMLInputElement>) => { setBeds(Number(e.target.value))}}
                />
              </Box>
              <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                <Bathroom sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField 
                  id="baths-input" 
                  type="number" 
                  label="Total Bathrooms" 
                  variant="standard" 
                  defaultValue={currentRoom?.baths}
                  onChange={(e: ChangeEvent<HTMLInputElement>) => { setBaths(Number(e.target.value))}}
                />
              </Box>
              <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                <AttachMoney sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField 
                  id="price-input" 
                  type="price" 
                  label="Price" 
                  variant="standard" 
                  defaultValue={currentRoom?.price}
                  onChange={(e: ChangeEvent<HTMLInputElement>) => { setPrice(Number(e.target.value))}}
                />
              </Box>
              
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseUpdate}>Cancel</Button>
              <Button type="submit">Save</Button>
            </DialogActions>
        </Dialog>
        
        {/* End Dialog To Update Room */}


        {/* Start Dialog To Add a Room */}
        <Dialog
          open={openAdd}
          onClose={handleCloseAdd}
          slotProps={{
            paper: {
              component: 'form',
              onSubmit: async (event: React.FormEvent<HTMLFormElement>) => {
                event.preventDefault();
                
                //Send the request to update the room
                try{
                  let res = await axios.post<{
                    type: string,
                    beds: number,
                    baths: number,
                    price: number
                  }>(`http://localhost:8080/hotels/${hotelId}/rooms`,
                    {
                      type: roomType,
                      beds: beds,
                      baths: baths,
                      price: price
                    },
                    {withCredentials: true}
                  )

                  console.log(res)
                  setShouldUpdate(!shouldUpdate)

                } catch (error){
                  console.error("Cannot add the room", error)
                }
                
                handleCloseAdd();
              },
            },
          }}
        >
            <DialogTitle>Add New Room</DialogTitle>
            <DialogContent sx={{alignItems:"center"}}>
              <DialogContentText>
                To add a new Room, please enter the data
              </DialogContentText>

              <TextField
                id="roomType-input"
                select
                label="Room Type"
                defaultValue="SINGLE"
                helperText="Please select the room type"
                size="small"
                sx={{
                  width: "50%",
                  marginTop: "15px"
                }}
                onChange={(e: ChangeEvent<HTMLInputElement>) => { setRoomType(e.target.value) }}
              >     
              <MenuItem key={"SINGLE"} value={"SINGLE"} >SINGLE</MenuItem>
              <MenuItem key={"DOUBLE"} value={"DOUBLE"} >DOUBLE</MenuItem>
              <MenuItem key={"TRIPLE"} value={"TRIPLE"} >TRIPLE</MenuItem>
              <MenuItem key={"SUITE"} value={"SUITE"} >SUITE</MenuItem>

              </TextField>
              <Box sx={{ display: 'flex', alignItems: 'flex-end'}}>
                <Bed sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField 
                  id="beds-input" 
                  type="number" 
                  label="Total Beds" 
                  variant="standard" 
                  onChange={(e: ChangeEvent<HTMLInputElement>) => { setBeds(Number(e.target.value))}}
                />
              </Box>
              <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                <Bathroom sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField 
                  id="baths-input" 
                  type="number" 
                  label="Total Bathrooms" 
                  variant="standard" 
                  onChange={(e: ChangeEvent<HTMLInputElement>) => { setBaths(Number(e.target.value))}}
                />
              </Box>
              <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                <AttachMoney sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField 
                  id="price-input" 
                  type="price" 
                  label="Price" 
                  variant="standard" 
                  onChange={(e: ChangeEvent<HTMLInputElement>) => { setPrice(Number(e.target.value))}}
                />
              </Box>
              
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseAdd}>Cancel</Button>
              <Button type="submit">Save</Button>
            </DialogActions>
        </Dialog>


    </div>
  )
}

export default Rooms