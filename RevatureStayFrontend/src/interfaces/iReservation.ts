import { IHotel } from "./IHotel";
import IRoom from "./iRoom";
import IUser from "./iUser";

export default interface IReservation {
  reservationId: number,
  checkInDate: Date,
  checkOutDate: Date,
  numGuest: number,
  status: "PENDING" | "ACCEPTED" | "REJECTED" | "CANCELLED" | "FINALIZED",
  user: IUser,
  hotel: IHotel,
  rooms: IRoom
}