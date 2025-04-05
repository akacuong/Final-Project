import { Injectable } from '@angular/core';
import { Service } from '../common/service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private readonly apiUrl = 'https://localhost:3306/api/services';

  constructor(private http: HttpClient) {}

  getServices(): Service[] {
    let services: Service[] = [];
    this.http.get<Service[]>(this.apiUrl)
      .subscribe(data => {
        services = data || [];
      });
    return services;
  }

  addService(service: Service): void {
    this.http.post(this.apiUrl, service)
      .subscribe(response => {
        console.log('Service added to backend');
      });
  }

  updateService(updatedService: Service): void {
    this.http.put(`${this.apiUrl}/${updatedService.id}`, updatedService)
      .subscribe(response => {
        console.log('Service updated in backend');
      });
  }

  deleteService(id: number): void {
    this.http.delete(`${this.apiUrl}/${id}`)
      .subscribe(response => {
        console.log('Service deleted from backend');
      });
  }
}