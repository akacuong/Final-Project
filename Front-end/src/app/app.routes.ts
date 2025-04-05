import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './pages/main/main.component';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { ProductComponent } from './pages/admin/products/products.component';
import { CategoryComponent } from './pages/admin/category/category.component';
import { AgentComponent } from './pages/admin/agent/agent.component';
import { ServiceComponent } from './pages/admin/service/service.component';
import { HairStylistComponent } from './pages/admin/hairstylist/hairstylist.component';
import { BookingComponent } from './pages/customer/booking/booking.component';
import { ServiceCustomerComponent } from './pages/customer/service-customer/service-customer.component';
import { ProductCustomerComponent } from './pages/customer/product-customer/product-customer.component';
import { ShopComponent } from './pages/admin/shop/shop.component';
import { ProductDetailComponent } from './pages/customer/product-detail/product-detail.component';
import { CartComponent } from './pages/customer/cart/cart.component';
import { ChartAnalyticsComponent } from './pages/admin/chart-analytics/chart-analytics.component';
import { PaymentComponent } from './pages/customer/payment/payment.component';
import { RegisterComponent } from './register/register.component';
import { CustomerInforComponent } from './pages/customer/customer-infor/customer-infor.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './auth.guard';
import { DashboardManagerComponent } from './pages/manager/dashboard-manager/dashboard-manager.component';
import { ManagerAccountComponent } from './pages/manager/manager-account/manager-account.component';

export const routes: Routes = [
  { path: '', component: MainComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { 
    path: 'admin/dashboard', 
    component: DashboardComponent,  
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] },
    children: [
      { path: 'products', component: ProductComponent },
      { path: 'category', component: CategoryComponent }, 
      { path: 'agent', component: AgentComponent },
      { path: 'service', component: ServiceComponent },
      { path: 'hairstylist', component: HairStylistComponent },
      { path: 'shop', component: ShopComponent },
      { path: 'analyst', component: ChartAnalyticsComponent }
    ]
  },
  { 
    path: 'manager/dashboard-manager', 
    component: DashboardManagerComponent,
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] },
    children: [
      { path: 'manager-account', component: ManagerAccountComponent }
    ]
  },
  { path: 'customer/booking', component: BookingComponent, canActivate: [AuthGuard], data: { roles: ['CUSTOMER'] } },
  { path: 'customer/service-customer', component: ServiceCustomerComponent, canActivate: [AuthGuard], data: { roles: ['CUSTOMER'] } },
  { path: 'customer/product-customer', component: ProductCustomerComponent, canActivate: [AuthGuard], data: { roles: ['CUSTOMER'] } },
  { path: 'product-detail/:id', component: ProductDetailComponent, canActivate: [AuthGuard], data: { roles: ['CUSTOMER'] } },
  { path: 'customer/cart', component: CartComponent, canActivate: [AuthGuard], data: { roles: ['CUSTOMER'] } },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule], 
  exports: [RouterModule] 
})
export class AppRoutingModule { }