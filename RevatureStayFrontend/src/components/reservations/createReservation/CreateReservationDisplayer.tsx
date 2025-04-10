import { useEffect, useState } from "react";
import { IHotel } from "../../../interfaces/IHotel";
import axios from "axios";
import { useParams } from "react-router-dom";
import { Box, CircularProgress } from "@mui/material";
import CreateReservation from "./CreateReservation";

function CreateReservationDisplayer(){
    const [error, setError] = useState<string>("");
    const [hotel, setHotel] = useState<IHotel|null>(null);
    const {hotelId} = useParams<{hotelId: string}>();

    const defaultHotel : IHotel = {
        hotelId: 0,
        name: "",
        address: "",
        cellphoneNumber:  "",
        description: "",
    }

    useEffect(() => {   
    
        let getHotel = async () => {
            try{
                let res = await axios.get<IHotel>(`http://localhost:8080/hotels/${hotelId}`)
                setHotel(res.data)
            } catch (error){
                if (axios.isAxiosError(error)) {
                    const errorMessage = error.response?.data?.error || error.message;
                    setError(`${error.response?.status}: ${errorMessage}`);
                } else {
                    setError('Unknown error: ' + error);
                }    
            }
        }
        
        getHotel();
    },[])

    if(!hotel && !error){
        return(
            
            <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
            height="10rem" 
            >
                <CircularProgress />
            
            </Box>
        );
    }

    if(error){
        return (
            <div style={{ textAlign: 'center', marginTop: '50px' }}>
              <p>{error}</p>
            </div>
        );
    }

    return(
        <CreateReservation hotel={hotel ?? defaultHotel}/>
    );
}

export default CreateReservationDisplayer;