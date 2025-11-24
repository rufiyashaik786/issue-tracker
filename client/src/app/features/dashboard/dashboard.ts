import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { IssueService } from '../issues/services/issue.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
})
export class DashboardComponent implements OnInit {
  totalIssues = 0;
  openIssues = 0;
  inProgressIssues = 0;
  resolvedIssues = 0;
  closedIssues = 0;

  highPriority = 0;
  mediumPriority = 0;
  lowPriority = 0;

  recentIssues: any[] = [];
  loading = true;

  constructor(private issueService: IssueService) {}

  ngOnInit(): void {
    this.loadDashboard();
    this.loadRecentIssues();
  }

  // ------------------------------
  // DASHBOARD SUMMARY
  // ------------------------------
  loadDashboard() {
    this.issueService.getDashboardSummary().subscribe({
      next: (summary: any) => {
        this.totalIssues = summary.totalIssues;
        this.openIssues = summary.openIssues;
        this.inProgressIssues = summary.inProgressIssues;
        this.resolvedIssues = summary.resolvedIssues;
        this.closedIssues = summary.closedIssues;

        this.highPriority = summary.highPriority;
        this.mediumPriority = summary.mediumPriority;
        this.lowPriority = summary.lowPriority;

        this.loading = false;
      },
      error: (err: any) => {
        console.error('Dashboard error', err);
        this.loading = false;
      },
    });
  }

  // ------------------------------
  // RECENT ISSUES LIST
  // ------------------------------
  loadRecentIssues() {
    this.issueService.getAll().subscribe({
      next: (res: any) => {
        const issues = res.data || res;
        this.recentIssues = issues.slice(0, 5);
      },
      error: (err: any) => {
        console.error('Recent error', err);
      },
    });
  }
}
