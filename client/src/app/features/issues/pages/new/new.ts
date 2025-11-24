import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IssueService } from '../../services/issue.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-issue-new',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './new.html',
  styleUrls: ['./new.css'],
})
export class IssueNewComponent implements OnInit {
  form!: FormGroup;
  backendError: string | null = null; // MUST BE ON SEPARATE LINE
  submitting = false; // ALSO SEPARATE LINE

  statuses = ['OPEN', 'IN_PROGRESS', 'CLOSED', 'RESOLVED'];

  priorities = ['LOW', 'MEDIUM', 'HIGH'];

  constructor(
    private fb: FormBuilder,
    private issueService: IssueService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]],
      status: ['OPEN', Validators.required],
      priority: ['MEDIUM', Validators.required],
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;

    this.issueService.create(this.form.value).subscribe({
      next: () => {
        this.toastr.success('Issue created successfully!');
        this.router.navigate(['/issues']);
      },
      error: () => {
        this.backendError = 'Failed to create issue.';
        this.toastr.error('Creation failed');
        this.submitting = false;
      },
    });
  }

  cancel() {
    this.router.navigate(['/issues']);
  }
}
