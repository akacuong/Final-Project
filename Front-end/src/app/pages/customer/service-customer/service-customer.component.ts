import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Service } from '../../../common/service';
import { ServiceService } from '../../../services/service.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-service-customer',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './service-customer.component.html',
  styleUrls: ['./service-customer.component.css']
})
export class ServiceCustomerComponent implements OnInit {
  services: Service[] = [];

  constructor(private serviceService: ServiceService, private router: Router) {}

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    this.services = this.serviceService.getServices();
  }

  bookService(service: Service): void {
    this.router.navigate([], { queryParamsHandling: 'merge' }).then(() => {
      const currentParams = this.router.getCurrentNavigation()?.extras?.queryParams;
      console.log("Current Params:", currentParams);
  
      let selectedServices: Service[] = currentParams && currentParams['selectedServices']
        ? JSON.parse(currentParams['selectedServices'])
        : [];
  
      if (!selectedServices.some(s => s.id === service.id)) {
        selectedServices.push({ ...service, checked: true });
      }
  
      console.log("Updated Selected Services:", selectedServices);
  
      this.router.navigate(['customer/booking'], {
        queryParams: { selectedServices: JSON.stringify(selectedServices) }
      });
    });
  }
  
  
  
  
  
}
