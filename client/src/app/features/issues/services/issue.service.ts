import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class IssueService {
  private api = 'http://localhost:8080/api/issues';

  constructor(private http: HttpClient) {}

  // -------------------------------------------------
  // BASIC CRUD
  // -------------------------------------------------
  getAll(): Observable<any> {
    return this.http.get(this.api);
  }

  getById(id: number): Observable<any> {
    return this.http.get(`${this.api}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(this.api, data);
  }

  update(id: number, data: any): Observable<any> {
    return this.http.put(`${this.api}/${id}`, data);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.api}/${id}`);
  }

  // -------------------------------------------------
  // LIST PAGE (pagination + filters)
  // -------------------------------------------------
  getIssues(params: any): Observable<any> {
    let query = new HttpParams()
      .set('page', params.page)
      .set('size', params.size)
      .set('sortBy', params.sortBy)
      .set('sortDir', params.sortDir);

    if (params.status) query = query.set('status', params.status);
    if (params.priority) query = query.set('priority', params.priority);
    if (params.search) query = query.set('search', params.search);

    return this.http.get(this.api, { params: query });
  }

  // -------------------------------------------------
  // DASHBOARD
  // -------------------------------------------------
  getDashboardSummary(): Observable<any> {
    return this.http.get(`${this.api}/dashboard/summary`);
  }

  // -------------------------------------------------
  // ATTACHMENTS (MATCHES YOUR BACKEND EXACTLY)
  // -------------------------------------------------

  // Upload file → POST /api/issues/{issueId}/attachments
  uploadAttachment(issueId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('files', file); // VERY IMPORTANT: backend expects "files"

    return this.http.post(`${this.api}/${issueId}/attachments`, formData);
  }

  // Download file → GET /api/issues/attachments/{attId}/download
  downloadAttachment(attId: number): Observable<Blob> {
    return this.http.get(`${this.api}/attachments/${attId}/download`, { responseType: 'blob' });
  }

  // Delete file → DELETE /api/issues/attachments/{attId}
  deleteAttachment(attId: number): Observable<any> {
    return this.http.delete(`${this.api}/attachments/${attId}`);
  }
}
