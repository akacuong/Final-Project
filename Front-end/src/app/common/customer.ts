import { Account } from './account';

export class Customer {
  constructor(
    public id?: number,
    public birthYear?: Date,
    public gender?: string,
    public hairStyle?: string,
    public point?: number,
    public imageFile?: string,
    public account?: Account
  ) {}
}