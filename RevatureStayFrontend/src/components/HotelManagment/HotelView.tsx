import { Box, Button,Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { IHotel } from "../../interfaces/IHotel";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { ArrowBack, Camera, ImportContacts, LocationPin, Phone } from "@mui/icons-material";
import { URL } from "../../util/path";
import ImageListCustomer from "./HotelCustomerView/ImageList";



function HotelView() {
    
    const {hotelId} = useParams<{hotelId: string}>();
    const [hotel, setHotel] = useState<IHotel>();
    const navigate = useNavigate()


    useEffect(() => {
        
            let getHotel = async () => {
                try {
                    let res = await axios.get<IHotel>(`${URL}/hotels/${hotelId}`)
                    setHotel(res.data)
                }
                catch (error){
                    console.error("Could not retrive hotel with id ", hotelId, " " , error)
                }
            }
        
        getHotel()
    },[])


  return (
    <>
        <div style={{ width: "50%", margin: "auto", position:"relative", marginTop:10}}>

            <Button color="secondary" variant="contained" style={{position:"absolute", top:10, right: -120}} onClick={() => navigate("/")} > <ArrowBack/> Back</Button>

            <div style={{textAlign:"center"}}>
                <Typography variant="h1" fontSize={"34px"}>{hotel?.name}</Typography>
                <p><LocationPin sx={{verticalAlign:"middle"}}/> {hotel?.address}</p>
                <p><Phone sx={{verticalAlign:"middle"}}/>{hotel?.cellphoneNumber}</p>
                <p>{hotel?.description}</p>
            </div>
            
            

            <h2 style={{textAlign: "left"}}><Camera sx={{verticalAlign:"middle"}}/> Photos:</h2>
            <Box sx={{ width: "90%", height: "auto", display:"flex", gap:5 }}>

                <ImageListCustomer hotelId={hotelId}/>

                <div style={{width:"20%"}}>

                    <div style={{display:"flex", flexDirection:"column", gap:"10px", marginTop: 10}}>
                        <Button color="secondary" variant="contained">Check Availability</Button>
                        <Button color="secondary" variant="contained"><ImportContacts/>Book Now!</Button>
                    </div>

                    <h3>Room Types</h3>
                    <ul style={{
                      listStyle:"none",
                      padding: 0
                      }}>
                        <li>Single</li>
                        <li>Double</li>
                        <li>Triple</li>
                        <li>Suites</li>
                    </ul>

                    
                </div>
            </Box>

            
        </div>

        
    </>
  )

}

export default HotelView