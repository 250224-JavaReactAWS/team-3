import { Card, CardMedia, CardContent, Typography, CardActions, Button, IconButton, Tooltip, Box } from "@mui/material"
import { IHotel } from "../../interfaces/IHotel"
import { useNavigate } from "react-router"
import axios from "axios"
import { useEffect, useState } from "react"
import { InImage } from "../../interfaces/InImage"
import { Delete, Edit } from "@mui/icons-material"
import { URL } from "../../util/path"

function HotelCardOwner(props: IHotel) {

  const navigate = useNavigate()


  const [images, setImages] = useState<InImage[]>([])
  const firstElement: string | undefined = images.length > 0 ? images[0].url : undefined;

  useEffect(() => {

    let getImageByHotelId = async () => {
      try {
        let res = await axios.get<InImage[]>(`${URL}/hotels/${props.hotelId}/images`)
        setImages(res.data)
        console.log(res)
      } catch (error){
        console.error("Error fetching hotel images ", error)
      }
      
    }
  
    getImageByHotelId()

  }, [])

  return (
    <Card sx={{ maxWidth: 345 }}>
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
        <Button color="secondary" size="small" onClick={()=> {navigate(`/my-hotels/${props.hotelId}/${props.name}/rooms`)}}>Manage Rooms</Button>
        <Button color="secondary" size="small" onClick={()=> {navigate(`/my-hotels/${props.hotelId}/${props.name}/images`)}}>Manage Images</Button>
        
        <Box>
          <Tooltip title="Edit">
            <IconButton>
              <Edit color="secondary" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Delete">
            <IconButton>
              <Delete color="error" />
            </IconButton>
          </Tooltip>
        </Box>
        
      </CardActions>
    </Card>
  )
}

export default HotelCardOwner