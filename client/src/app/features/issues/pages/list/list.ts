import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { IssueService } from '../../services/issue.service';

import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-issue-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './list.html',
  styleUrls: ['./list.css'],
})
export class IssueListComponent implements OnInit {
  issues: any[] = [];

  searchText = '';
  statusFilter = '';
  priorityFilter = '';
  sortDirection: 'asc' | 'desc' = 'desc';

  page = 0;
  size = 10;
  totalPages = 0;

  loading = false;

  constructor(private issueService: IssueService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.loadIssues();
  }

  loadIssues() {
    this.loading = true;

    const params = {
      page: this.page,
      size: this.size,
      sortBy: 'createdAt',
      sortDir: this.sortDirection,
      status: this.statusFilter,
      priority: this.priorityFilter,
      search: this.searchText,
    };

    this.issueService.getIssues(params).subscribe({
      next: (res: any) => {
        this.issues = res.data || [];
        this.totalPages = res.totalPages || 1;
        this.loading = false;
      },
      error: () => {
        this.toastr.error('Failed to load issues');
        this.loading = false;
      },
    });
  }

  onFilterChange() {
    this.page = 0;
    this.loadIssues();
  }

  toggleSort() {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.loadIssues();
  }

  nextPage() {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.loadIssues();
    }
  }

  prevPage() {
    if (this.page > 0) {
      this.page--;
      this.loadIssues();
    }
  }

  deleteIssue(id: number) {
    if (!confirm('Are you sure?')) return;

    this.issueService.delete(id).subscribe({
      next: () => {
        this.toastr.success('Issue deleted');
        this.loadIssues();
      },
      error: () => this.toastr.error('Failed to delete issue'),
    });
  }

  get filteredIssues() {
    let data = [...this.issues];

    if (this.searchText) {
      const s = this.searchText.toLowerCase();
      data = data.filter((i) => i.title?.toLowerCase().includes(s));
    }

    return data;
  }
}
