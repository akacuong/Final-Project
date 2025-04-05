import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../common/account';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-manager-account',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './manager-account.component.html',
  styleUrls: ['./manager-account.component.css']
})
export class ManagerAccountComponent implements OnInit {
  pendingAgents: Account[] = [];

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.loadPendingAgents();
  }

  loadPendingAgents(): void {
    this.accountService.getPendingAgents().subscribe((agents: Account[]) => {
      this.pendingAgents = agents;
    });
  }

  approveAgent(agentId?: number): void {
    if (agentId !== undefined) {
      this.accountService.approveAgent(agentId).subscribe(() => {
        this.loadPendingAgents();
      });
    }
  }

  rejectAgent(agentId?: number): void {
    if (agentId !== undefined) {
      this.accountService.rejectAgent(agentId).subscribe(() => {
        this.loadPendingAgents();
      });
    }
  }
}