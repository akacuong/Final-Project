import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Service } from '../../../common/service';
import { ServiceService } from '../../../services/service.service';

@Component({
  selector: 'app-service',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.css']
})
export class ServiceComponent implements OnInit {
  services: Service[] = [];
  newService: Service = new Service(0, '', 0, '', '');
  selectedService: Service | null = null;

  constructor(private serviceService: ServiceService) {}

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    this.services = this.serviceService.getServices();
  }

  addService(): void {
    if (!this.newService.name.trim() || this.newService.price <= 0 || !this.newService.image.trim()) return;
  
    const services = this.serviceService.getServices();
    const newId = services.length ? Math.max(...services.map(s => s.id)) + 1 : 1;
  
    this.newService.id = newId;
    this.serviceService.addService(this.newService);
    this.newService = new Service(0, '', 0, '', ''); 
    this.loadServices();
  }
  
  editService(service: Service): void {
    this.selectedService = { ...service };
  }

  updateService(): void {
    if (this.selectedService) {
      this.serviceService.updateService(this.selectedService);
      this.selectedService = null;
      this.loadServices();
    }
  }

  deleteService(id: number): void {
    this.serviceService.deleteService(id);
    this.loadServices();
  }

  resetForm(): void {
    this.selectedService = null;
    this.newService = new Service(0, '', 0, '', '');
  }
}
