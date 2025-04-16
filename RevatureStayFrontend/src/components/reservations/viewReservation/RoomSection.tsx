import { Button, Dialog, DialogContent, DialogTitle, FormControl, Grid, InputLabel, MenuItem, Select, Typography } from "@mui/material";
import iRoom from "../../../interfaces/iRoom";
import Room from "./Room";
import { useState } from "react";
import axios from "axios";
import {URL} from "../../../util/path";

interface IRoomSection{
    rooms : iRoom[],
    setRooms:(value: iRoom[]) => void,
    reservationId: number
}

function RoomSection({rooms, reservationId, setRooms}: IRoomSection){
    const [roomType, setRoomType] = useState<string>("");
    const [isDisabled, setIsDisabled] = useState<boolean>(true);
    const [error, setError] = useState<string>("");

    const handleDelete =async (roomId:number) => {
        try{
                await axios.delete<any>(`${URL}/reservations/${reservationId}/rooms/${roomId}`,
                {withCredentials: true}
            )
            let newRooms:iRoom[] = rooms.filter((room) => room.roomId !== roomId)
            setRooms(newRooms);

            } catch (error){
            if (axios.isAxiosError(error)) {
                const errorMessage = error.response?.data?.error || error.message;
                setError(errorMessage);
            } else {
                setError('Unknown error: ' + error);
            }    
        }
    }

    const handleAdd = async (value:string) => {
        try{
            let res = await axios.post<iRoom>(`${URL}/reservations/${reservationId}/rooms`,
                {type:value.toUpperCase()},
                {withCredentials: true}
            )
            setRooms([...rooms, res.data]);
            setRoomType("");
            } catch (error){
            if (axios.isAxiosError(error)) {
                const errorMessage = error.response?.data?.error || error.message;
                setError(errorMessage);
            } else {
                setError('Unknown error: ' + error);
            }    
        }
    }

    return(
        <>
        <Dialog open={error ? true: false} onClose={() => {setError("")}}>
            <DialogTitle>Upps something went wrong!</DialogTitle>
            <DialogContent>
                <Typography>
                {`${error} . Please try again`}
                </Typography>
            </DialogContent>
        </Dialog>
        <Grid container spacing={2}>
            {rooms.map((room) => (
            <Grid size={{xs:12, sm:6, md:3}} key={room.roomId}>
                <Room room={room} handleDelete={handleDelete} />
            </Grid>
            ))}
            
        </Grid>
        <Grid container spacing={2} alignItems={"end"} margin={"1rem"}>
            <Grid>
                <FormControl size="small">
                    <InputLabel id="demo-simple-select-label">Add room of type</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={roomType}
                    label="Add room of type"
                    onChange={(event)=>{
                            setRoomType(event.target.value)
                            setIsDisabled(false);
                        }}
                    sx={{width:"12rem"}}
                    >
                        <MenuItem value={"SUITE"}>SUITE</MenuItem>
                        <MenuItem value={"DOUBLE"}>DOUBLE</MenuItem>
                        <MenuItem value={"SINGLE"}>SINGLE</MenuItem>
                    </Select>
                </FormControl>
            </Grid>
            <Grid>
                <Button variant="contained" color="primary" disabled={isDisabled} onClick={()=>{handleAdd(roomType)}}>
                    Add room
                </Button>
            </Grid>    
        </Grid>
        
           </>
    );
}

export default RoomSection;