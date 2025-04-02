export class Service {
    constructor(
      public id: number,
      public name: string,
      public price: number,
      public status: string,
      public image: string,
      public checked?: boolean,
    ) {}
  }
  