import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Chart, ChartConfiguration, ChartType } from 'chart.js';

@Component({
  selector: 'app-chart-analytics',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chart-analytics.component.html',
  styleUrls: ['./chart-analytics.component.css']
})
export class ChartAnalyticsComponent implements OnInit {
  @ViewChild('chartCanvas', { static: true }) chartCanvas!: ElementRef;
  private chart!: Chart;

  ngOnInit() {
    this.renderChart();
  }

  private renderChart() {
    const ctx = this.chartCanvas.nativeElement.getContext('2d');

    const config: ChartConfiguration = {
      type: 'bar' as ChartType, // Chọn loại biểu đồ: bar, line, pie, etc.
      data: {
        labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5'],
        datasets: [
          {
            label: 'Doanh thu',
            data: [12000, 15000, 18000, 20000, 22000],
            backgroundColor: ['#007bff', '#28a745', '#ffc107', '#dc3545', '#17a2b8'],
          }
        ]
      },
      options: {
        responsive: true,
        scales: {
          y: { beginAtZero: true }
        }
      }
    };

    this.chart = new Chart(ctx, config);
  }
}
