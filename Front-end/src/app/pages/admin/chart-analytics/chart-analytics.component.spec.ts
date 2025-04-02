import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartAnalyticsComponent } from './chart-analytics.component';

describe('ChartAnalyticsComponent', () => {
  let component: ChartAnalyticsComponent;
  let fixture: ComponentFixture<ChartAnalyticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChartAnalyticsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChartAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
