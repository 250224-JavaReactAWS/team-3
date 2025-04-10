import { Button, FormControl, Grid, InputLabel, MenuItem, Select } from "@mui/material";
import { IRoom } from "../../../interfaces/IRoom";
import Room from "./Room";
import { useState } from "react";

interface IRoomSection{
    rooms : IRoom[]
}

function RoomSection({rooms}: IRoomSection){
    const [roomType, setRoomType] = useState<string>("");
    const [isDisabled, setIsDisabled] = useState<boolean>(true);
    const handleDelete = (roomId:number) => {
        console.log(`Deleted room ${roomId}`);
    }

    return(
        <>
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
                        <MenuItem value={10}>SUITE</MenuItem>
                        <MenuItem value={20}>DOUBLE</MenuItem>
                        <MenuItem value={30}>SIMPLE</MenuItem>
                    </Select>
                </FormControl>
            </Grid>
            <Grid>
                <Button variant="contained" color="primary" disabled={isDisabled}>
                    Add room
                </Button>
            </Grid>    
        </Grid>
        
           </>
    );
}

export default RoomSection;