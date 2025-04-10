export interface IRoom {
    roomId: number,
    type: "SINGLE" | "DOUBLE" | "TRIPLE" | "SUITE",
    beds: number,
    baths: number,
    price: number,
    available: boolean
}