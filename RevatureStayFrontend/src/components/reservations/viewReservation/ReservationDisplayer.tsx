import axios from "axios";
import { useContext, useEffect, useState } from "react";
import iReservation from "../../../interfaces/iReservation";
import { useParams } from "react-router-dom";
import { Box, CircularProgress } from "@mui/material";
import Reservation from "./Reservation";
import { AuthContextType, authContext } from "../../../App";
import OwnedReservation from "./OwnedReservation";

function ReservationDisplayer(){
    const {reservationId} = useParams<{reservationId: string}>();
    const [reservation, setReservation] = useState<iReservation | null>(null);
    const [error, setError] = useState<string>("");
    const roleReference = useContext<AuthContextType|null>(authContext);

    const defaultReservation : iReservation= {
        checkInDate: "",
        checkOutDate: "",
        numGuests: 0,
        rooms: [],
        hotel: {
            hotelId: 0,
            name: "",
            address: "",
            cellphoneNumber:  "",
            description: "",
        },
        reservationId:0,
        user:{
            userId: 0,     
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            role: "CUSTOMER"
        },
        status:'PENDING'
    }

    useEffect(() => {
        
        let getReservation = async () => {
            try{
                let res = await axios.get<iReservation>(`http://localhost:8080/reservations/${reservationId}`, {withCredentials:true})
                setReservation(res.data)
              } catch (error){
                if (axios.isAxiosError(error)) {
                    const errorMessage = error.response?.data?.error || error.message;
                    setError(`${error.response?.status}: ${errorMessage}`);
                  } else {
                    setError('Unknown error: ' + error);
                  }    
              }
            
        }
    
        getReservation()
    },[])

    if(!reservation && !error){
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
    if(roleReference?.role === 'OWNER'){
        return(
            <OwnedReservation reservation={reservation ?? defaultReservation}/>
        );
    }
    return(
        <Reservation reservation={reservation ?? defaultReservation}/>
    );
}

export default ReservationDisplayer;