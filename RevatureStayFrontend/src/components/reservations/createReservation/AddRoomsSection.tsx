import { Box, Button, Chip, FormControl, MenuItem, Select, Typography } from "@mui/material";
import iRoom from "../../../interfaces/iRoom";
import { useState } from "react";

interface IAddRoom{
    rooms: iRoom[],
    setRooms: (rooms:iRoom[]) => void
}

function AddRoomsSection({rooms, setRooms}:IAddRoom){
    const [selectedRoomType, setSelectedRoomType] = useState<string>('');
    const roomTypes = ['Single', 'Double', 'Suite'];
    const [counter, setCounter] = useState<number>(0);
    const addRoom = () => {
        if (selectedRoomType) {
            setCounter(counter+1);
          const newRoom: iRoom = {
            roomId: counter,
            type: selectedRoomType.toUpperCase(),
            beds: 0,
            baths: 0,
            price: 0,
            isAvaiable: true
          };
          setRooms([...rooms, newRoom]); 
          setSelectedRoomType(''); 
        }
      };
      
      const handleDelete = (roomIdToDelete: number) => {
        let newRooms:iRoom[] = rooms.filter((room) => room.roomId !== roomIdToDelete)
        setRooms(newRooms);
      };
    
    return (
        <>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300 }}>
            <FormControl fullWidth>
                <Select
                    value={selectedRoomType}
                    onChange={(e) => setSelectedRoomType(e.target.value)}
                    displayEmpty
                    MenuProps={{
                        PaperProps: {
                          sx: {
                            '& ul': {
                              display: 'block !important', // 💥 override al global
                            },
                          },
                        },
                    }}
                >
                    <MenuItem value="" disabled>
                    Select type of room
                    </MenuItem>
                    {roomTypes.map((type) => (
                    <MenuItem key={type} value={type}>
                        {type}
                    </MenuItem>
                    ))}
                </Select>
                </FormControl>
        
                <Button variant="contained" onClick={addRoom}>
                Add Room
                </Button>
                <Box>
                    {rooms.length > 0 ? (
                        <Typography variant="h6" gutterBottom>
                        Requested Rooms
                        </Typography>
                    ): <></>}

                    
                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1 }}>
                    {rooms.map((room) => (
                        <Chip 
                            key={room.roomId} 
                            label={room.type} 
                            color="primary"
                            onDelete={()=> handleDelete(room.roomId)} />
                    ))}
                    </Box>
                </Box>
            </Box>   
        </>
    );
    
}

export default AddRoomsSection;