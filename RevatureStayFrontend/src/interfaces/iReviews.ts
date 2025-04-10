import { IHotel } from "./IHotel";
import IUser from "./iUser";

export default interface IReview {
  reviewId: number,
  title: string,
  comment: string,
  rate: number,
  user: IUser,
  hotel: IHotel
}