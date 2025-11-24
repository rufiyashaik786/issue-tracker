import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { IssueService } from '../../services/issue.service';

import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-issue-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detail.html',
  styleUrls: ['./detail.css'],
})
export class IssueDetailComponent implements OnInit {
  issue: any = null;
  loading = true;

  selectedFile: File | null = null;
  uploadMessage = '';

  constructor(
    private route: ActivatedRoute,
    private issueService: IssueService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) this.loadIssue(id);
  }

  loadIssue(id: number) {
    this.loading = true;
    this.issueService.getById(id).subscribe({
      next: (res: any) => {
        this.issue = res.data || res;
        this.loading = false;
      },
      error: () => {
        this.toastr.error('Failed to load issue');
        this.loading = false;
      },
    });
  }

  goBack() {
    this.router.navigate(['/issues']);
  }

  edit() {
    this.router.navigate(['/issues', this.issue.id, 'edit']);
  }

  confirmDelete() {
    Swal.fire({
      title: 'Delete issue?',
      text: 'This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteIssue();
      }
    });
  }

  deleteIssue() {
    this.issueService.delete(this.issue.id).subscribe({
      next: () => {
        this.toastr.success('Issue deleted');
        this.router.navigate(['/issues']);
      },
      error: () => {
        this.toastr.error('Failed to delete');
      },
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  upload() {
    if (!this.selectedFile) {
      this.toastr.error('Please select a file');
      return;
    }

    this.issueService.uploadAttachment(this.issue.id, this.selectedFile).subscribe({
      next: () => {
        this.toastr.success('Uploaded!');
        this.loadIssue(this.issue.id);
      },
      error: () => {
        this.toastr.error('Upload failed');
      },
    });
  }

  downloadAttachment(attId: number) {
    this.issueService.downloadAttachment(attId).subscribe((blob: Blob) => {
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }

  deleteAttachment(attId: number) {
    Swal.fire({
      title: 'Delete attachment?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
    }).then((res) => {
      if (res.isConfirmed) {
        this.issueService.deleteAttachment(attId).subscribe({
          next: () => {
            this.toastr.success('Attachment deleted');
            this.loadIssue(this.issue.id);
          },
          error: () => {
            this.toastr.error('Failed');
          },
        });
      }
    });
  }
}
