import { Box, CircularProgress, Typography } from "@mui/material";
import iReservation from "../../../interfaces/iReservation";
import ListedReservation from "./ListedReservation";
import ReservationListHeader from "./ReservationListHeader";
import { useEffect, useState } from "react";
import axios from "axios";
import {URL} from "../../../util/path";

function ReservationsList(){
    const [reservations, setReservations] = useState<iReservation[]>([]);
    const [error, setError] = useState<string>("");

    useEffect(() => {   
    
        let getHotel = async () => {
            try{
                let res = await axios.get<iReservation[]>(`${URL}/reservations`, {withCredentials:true})
                setReservations(res.data)
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

    if(reservations.length===0 && !error){
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
        <>
            <ReservationListHeader/>
            <div>
                {reservations.length ===0 ? (
                    <Typography variant="body1">There are not reservations to show</Typography>
                ):(
                    reservations.map((reservation, index )=>(
                        <ListedReservation key={`${reservation}-${index}`} {...reservation}/>
                    ))
                )
                }
            </div>
        </>
    );
}

export default ReservationsList;