export interface Issue {
  id: number;
  title: string;
  description: string;
  status: string;
  priority: string;

  // Your backend sends these timestamps
  createdAt: string;
  updatedAt: string;
}
