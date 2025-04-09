import { IReservation } from "../../interfaces/IReservation";
import ListedReservation from "./ListedReservation";
import ReservationListHeader from "./ReservationListHeader";

interface ReservationsListProps {
    reservations: IReservation[];
  }  
function ReservationsList({reservations}: ReservationsListProps){
    return(
        <>
            <ReservationListHeader/>
            <div>
                {reservations.map((reservation, index )=>(
                    <ListedReservation key={`${reservation}-${index}`} {...reservation}/>
                ))}
            </div>
        </>
    );
}

export default ReservationsList;