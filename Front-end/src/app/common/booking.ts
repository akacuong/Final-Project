export class Booking {
    constructor(
        public booking_id: number,
        public shopId: number,
        public stylist_id: number,
        public datetime: string,
        public status: string,
        public payment_status: string,
        public total_price: number,
        public customer_id: number,
        public customer_phone: string,
        public customer_email?: string
    ){}
}

