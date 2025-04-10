import { Box } from "@mui/material"
import HotelCard from "./HotelCard"
import { useEffect, useState } from "react"
import axios from "axios"
import { IHotel } from "../../interfaces/IHotel"


function Hotels() {

  
  const [hotels, setHotels] = useState<IHotel[]>([])

  useEffect(() => {

    let getHotels = async () => {
      let res = await axios.get<IHotel[]>('http://localhost:8080/hotels')
      setHotels(res.data)
    }

    getHotels()
<<<<<<< HEAD
  },[])
=======
  }, [])
>>>>>>> 0aeec2616d0ef0541c9028c37331862ab354929f



  return (
    <Box sx={{width: "50%", margin:"auto"}}>
      <h1 style={{textAlign:"center", fontFamily: "Playfair Display"}}>Hotels</h1>

      <div style={{
        display: "grid",
        gridTemplateColumns: "auto auto auto",
        gap: "20px"
      }}>
        {
          hotels.map((hotel) => {
            return (
              <HotelCard
                key={hotel.hotelId}
                hotelId={hotel.hotelId} 
                name={hotel.name} 
                address={hotel.address} 
                cellphoneNumber={hotel.cellphoneNumber} 
                description={hotel.description} 
              ></HotelCard>
            )
          })
        }
        
      </div>
      
    </Box>
    
  )
}

export default Hotels