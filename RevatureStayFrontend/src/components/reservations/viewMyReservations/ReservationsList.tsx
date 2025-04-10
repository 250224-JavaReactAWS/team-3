import { Typography } from "@mui/material";
import { IReservation } from "../../../interfaces/IReservation";
import ListedReservation from "./ListedReservation";
import ReservationListHeader from "./ReservationListHeader";
import { useEffect, useState } from "react";
import axios from "axios";

function ReservationsList(){
    const [reservations, setReservations] = useState<IReservation[]>([]);

    useEffect(()=>{
        let getReservations = async () => {
            let response = await axios.get<IReservation[]>('http://localhost:8080/reservations');
            setReservations(response.data);
        }

        getReservations();
    }, [])

    return(
        <>
            <ReservationListHeader/>
            <div>
                {!reservations || reservations.length === 0 ? (
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