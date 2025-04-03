export class CustomerLoyalty {
    constructor(
        public id: number,
        public customer: string,
        public convertPoints: number,
        public datetime: Date,
        public customerId: number,  
        public accountId: number,   
    ){}
}