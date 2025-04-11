import {IHotel}  from "./IHotel";
import iRoom from "./iRoom";
import iUser from "./iUser";

export default interface IReservation {
  reservationId: number,
  checkInDate: string,
  checkOutDate: string,
  numGuests: number,
  status: "PENDING" | "ACCEPTED" | "REJECTED" | "CANCELLED" | "FINALIZED",
  user: iUser,
  hotel: IHotel,
  rooms: iRoom[]
}