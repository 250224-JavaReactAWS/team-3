import { IUser } from "./IUser";
import { IHotel } from "./IHotel";
import { IRoom } from "./IRoom";

export interface IReservation {
  reservationId: number;
  checkInDate: string;
  checkOutDate: string;
  numGuests: number;
  status: string;
  user: IUser;
  hotel: IHotel;
  rooms: IRoom[];
}
  