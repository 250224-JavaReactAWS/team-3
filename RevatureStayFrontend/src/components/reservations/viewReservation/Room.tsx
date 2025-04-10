import { Button, Card, CardActionArea, CardActions, CardContent, CardHeader, Typography } from "@mui/material";
import { IRoom } from "../../../interfaces/IRoom";
import DeleteRoomButton from "./DeleteRoomButton";
interface IRoomCard{
    room : IRoom,
    handleDelete: (reservationId:number)=>void
}

function Room({room, handleDelete} : IRoomCard){
    const handleDeleteWithRoom = () => {handleDelete(room.roomId)}
    return(
        <Card>
            <CardHeader title={`Room number ${room.roomId}`}/>
            <CardContent>
                <Typography variant="h6" gutterBottom>
                
                </Typography>
                <Typography variant="body1">
                Type: {room.type}
                </Typography>
                <Typography variant="body1">
                Beds: {room.beds}
                </Typography>
                <Typography variant="body1">
                Baths: {room.baths}
                </Typography>
                <Typography variant="body1">
                Price: ${room.price}
                </Typography>
            </CardContent>
            <CardActions>
                <DeleteRoomButton handleDelete={handleDeleteWithRoom}/>
            </CardActions>
        </Card>


    );
}

export default Room;