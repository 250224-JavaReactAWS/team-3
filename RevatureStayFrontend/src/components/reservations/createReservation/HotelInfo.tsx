import { Card, CardMedia, CardContent, Typography, Box, CircularProgress } from "@mui/material"
import { IHotel } from "../../../interfaces/IHotel";

interface IHotelInfo{
  props: IHotel | null
}
function HotelInfo({props} : IHotelInfo) {
  if(!props){
    return(
      <>
      </>
    );
  }
  return (
    <Card 
      sx={{ maxWidth: 400 }}
    >
      <CardMedia
        sx={{ height: 140 }}
        image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSb1N_lcHUtjRMh7b31uYtH7n-xNzIZGZzefg&s"
        title={props.name}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {props.name}
        </Typography>
        <Typography variant="h6" >
          Adress:
        </Typography>
        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
          {props.address}
        </Typography>
        <Typography variant="h6" >
          Phone number:
        </Typography>
        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
          {props.cellphoneNumber}
        </Typography>
      </CardContent>
    </Card>
  )
}

export default HotelInfo;