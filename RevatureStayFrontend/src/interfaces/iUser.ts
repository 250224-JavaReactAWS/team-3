import { IHotel } from "./IHotel";
import IReservation from "./iReservation";
import IReview from "./iReviews";

export default interface IUser {
  userId: number,     
  firstName: string,
  lastName: string,
  email: string,
  password: string,
  userRole: "CUSTOMER" | "OWNER",
  reservations: IReservation[],
  reviews: IReview[],
  ownerHotels: IHotel[] | null,
  hotels: IHotel[]
}