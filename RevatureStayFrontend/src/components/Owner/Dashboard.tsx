import { Box, Fab, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import { IHotel } from '../../interfaces/IHotel'
import axios from 'axios'

import HotelCardOwner from '../HotelManagment/HotelCardOwner'
import { Add } from '@mui/icons-material'
import HotelFormDialog from '../HotelManagment/NewHotelDialog'


function dashboard() {

    const [myHotels, setMyHotels] = useState<IHotel[]>([])
    //const [shouldUpdate, setShouldUpdate] = useState(false)

    const [open, setOpen] = useState(false)
    const [update, setUpdate] = useState(false)

    // Function to open the dialog
    const handleOpen = () => {
        setOpen(true);
    };

    // Function to close the dialog
    const handleClose = () => {
        setOpen(false);
    };

    const updateHotels = () =>{
        setUpdate(!update)
    }



    useEffect(() => {
        let getMyHotels = async() =>{
            try{
                let res = await axios.get<IHotel[]>("http://localhost:8080/hotels/my-hotels", 
                    {withCredentials: true}
                )
                setMyHotels(res.data)
            }catch(error){
                console.error("Could find your hotels ", error)
            }
        }


        getMyHotels()
    }, [update])
    



  return (
    <>
    <Box sx={{width:"50%", margin: "auto", position: "relative"}}>
        <Typography variant='h1' fontSize={34} sx={{margin: "15px 0 30px 0"}}>My hotels</Typography>
        <Fab color="primary" aria-label="add" sx={{position: "absolute", top:0, right:20}} onClick={() => handleOpen()}>
            <Add />
        </Fab>

        {   myHotels.length === 0 &&
            <Typography variant='h3' color='error' fontSize={26} marginTop={15}>Ups there are not any images here! Add one</Typography>
        }
        
        <div style={{
        display: "grid",
        gridTemplateColumns: "auto auto auto",
        gap: "20px"
        }}>

        {
        myHotels.map((hotel) => {
            return (
            <HotelCardOwner
                //Props
                key={hotel.hotelId}
                hotelId={hotel.hotelId} 
                name={hotel.name} 
                address={hotel.address} 
                cellphoneNumber={hotel.cellphoneNumber} 
                description={hotel.description}>
            </HotelCardOwner>
            )
        })
        }
        
        </div>

        
    </Box>

    {/* Render the ImgFormDialog component conditionally */}
        {open && <HotelFormDialog open={open} onClose={handleClose} updateHotels={updateHotels} /> }
    </>
  )
}

export default dashboard