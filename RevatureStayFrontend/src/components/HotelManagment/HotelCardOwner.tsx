import { Card, CardMedia, CardContent, Typography, CardActions, Button } from "@mui/material"
import { IHotel } from "../../interfaces/IHotel"
import { useNavigate } from "react-router"

function HotelCardOwner(props: IHotel) {

  const navigate = useNavigate()

  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardMedia
        sx={{ height: 140 }}
        image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSb1N_lcHUtjRMh7b31uYtH7n-xNzIZGZzefg&s"
        title={props.name}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {props.name}
        </Typography>
        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
          {props.description}
        </Typography>
      </CardContent>
      <CardActions>
        <Button color="secondary" size="small" onClick={()=> {navigate(`/my-hotels/${props.hotelId}/rooms`)}}>Manage Rooms</Button>
        <Button color="secondary" size="small" onClick={()=> {navigate(`/my-hotels/${props.hotelId}/images`)}}>Manage Images</Button>

      </CardActions>
    </Card>
  )
}

export default HotelCardOwner