
import { OrderDetail } from "./order-detail";

export class Order {
  public orderId?: number;
  customerId: number;
  status: string = "pending";  
  paymentStatus: string = "unpaid";
  totalPrice: number = 0;
  orderDetails: OrderDetail[] = [];

  constructor(customerId: number) {
    this.customerId = customerId;
  }
}
