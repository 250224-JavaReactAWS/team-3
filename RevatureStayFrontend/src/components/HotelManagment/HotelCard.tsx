import { Card, CardMedia, CardContent, Typography, CardActions, Button } from "@mui/material"
import { IHotel } from "../../interfaces/IHotel"
import { useEffect, useState } from "react"
import { InImage } from "../../interfaces/InImage"
import axios from "axios"
import { useNavigate } from "react-router"

function HotelCard(props: IHotel) {

  const navigate = useNavigate()

  const [images, setImages] = useState<InImage[]>([])
  const firstElement: string | undefined = images.length > 0 ? images[0].url : undefined;


  

  let getImageByHotelId = async () => {
    try {
      let res = await axios.get<InImage[]>(`http://localhost:8080/hotels/${props.hotelId}/images`)
      setImages(res.data)
    } catch (error){
      console.error("Error fetching hotel images ", error)
    }
    
  }

  getImageByHotelId()
    

  return (
    <Card 
      sx={{ maxWidth: 345 }}
    >
      <CardMedia
        sx={{ height: 140 }}
        image={firstElement === undefined ? "https://cdn-icons-png.freepik.com/512/6269/6269517.png" : images[0].url}
        title={props.name}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {props.name}
        </Typography>
        <Typography variant="body2" sx={{ color: 'text.secondary', height:40, overflow: "hidden" }}>
          {props.description}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small" onClick={() => {navigate(`/reservations/create/${props.hotelId}`)}}>Book Now!</Button>
        <Button size="small" onClick={()=> {navigate(`/hotels/${props.hotelId}`)}}>View</Button>
        <Button size="small">Add Favorites</Button>
      </CardActions>
    </Card>
  )
}

export default HotelCard