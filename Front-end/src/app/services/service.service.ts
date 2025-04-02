import { Injectable } from '@angular/core';
import { Service } from '../common/service';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private storageKey = 'services';
  private services: Service[] = [];
  constructor() {}

  getServices(): Service[] {
    
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data) : [];
  }

  addService(service: Service): void {
    const services = this.getServices();
    services.push(service);
    localStorage.setItem(this.storageKey, JSON.stringify(services));
  }

  updateService(updatedService: Service): void {
    const services = this.getServices().map(service =>
      service.id === updatedService.id ? updatedService : service
    );
    localStorage.setItem(this.storageKey, JSON.stringify(services));
  }

  deleteService(id: number): void {
    let services = this.getServices().filter(service => service.id !== id);
    localStorage.setItem(this.storageKey, JSON.stringify(services));
  }
  
}
