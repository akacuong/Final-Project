import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceCustomerComponent } from './service-customer.component';

describe('ServiceCustomerComponent', () => {
  let component: ServiceCustomerComponent;
  let fixture: ComponentFixture<ServiceCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiceCustomerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
