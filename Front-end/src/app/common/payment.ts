export class Payment {
    constructor(
    public    paymentId: number,
    public    orderId: number,
    public    bookingId: number,
    public    paymentMethod: string,
    public    transactionDate: string, 
    ){}
}
