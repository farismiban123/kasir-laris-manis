# kasir-laris-manis
  NAMA : FARIS MUHAMMAD RABBANI, DHIFAN ALTAF SAFARAZ, MUHAMMAD FIKRI RABBANI
  
  NPM :  140810250096, 140810250069, 140810250021

## Deskripsi
Sistem Digitalisasi Warung Nasi Laris Manis merupakan aplikasi desktop yang dirancang untuk membantu pemilik warung dalam mengelola daftar menu, mencatat pesanan pelanggan, serta memantau pendapatan harian. Aplikasi ini berfokus pada tiga fungsi utama, yaitu pengelolaan menu makanan dan minuman, pencatatan pesanan berdasarkan urutan pelanggan, serta penyajian laporan pendapatan harian secara otomatis.

## Tujuan
1. Mendigitalisasi proses transaksi di Warung Nasi Laris Manis.
2. Mengelola daftar menu makanan dan minuman.
3. Mengelola persediaan bahan baku.
4. Menghubungkan menu dengan bahan baku melalui sistem resep.
5. Menghitung ketersediaan menu secara otomatis berdasarkan stok bahan baku.
6. Mengurangi stok bahan baku secara otomatis ketika terjadi transaksi.
7. Memberikan nomor antrean pelanggan secara otomatis.
8. Menghitung total harga pesanan secara otomatis.
9. Menyimpan riwayat transaksi pelanggan.
10. Menyediakan laporan jumlah pesanan dan pendapatan harian.

## Fitur Utama

### 1. Manajemen Menu

Digunakan untuk mengelola daftar makanan dan minuman yang tersedia.

Informasi menu meliputi:
- Nama menu.
- Kategori menu (Makanan/Minuman).
- Harga jual.
- Status menu (Aktif/Tidak Aktif).
- Resep bahan baku yang digunakan.

### 2. Manajemen Bahan Baku

Digunakan untuk mengelola persediaan bahan baku yang digunakan dalam proses pembuatan menu.

Informasi bahan baku meliputi:
- Nama bahan baku.
- Satuan bahan (gram, kilogram, butir, porsi, ml, dan sebagainya).
- Jumlah stok tersedia.
- Batas minimum stok.

Fitur:
- Menambahkan stok bahan baku.
- Memantau jumlah stok.
- Melihat riwayat bahan baku masuk dan keluar.
- Mengetahui bahan baku yang hampir habis.

### 3. Sistem Resep Menu

Digunakan untuk menentukan kebutuhan bahan baku setiap menu.

Contoh:

| Menu | Bahan Baku | Kebutuhan |
|---|---|---|
| Ayam Bakar | Ayam | 1 porsi |
| Ayam Bakar | Nasi | 1 porsi |
| Ayam Goreng | Ayam | 1 porsi |
| Ayam Goreng | Nasi | 1 porsi |
| Nasi Telur | Nasi | 1 porsi |
| Nasi Telur | Telur | 1 butir |

Satu bahan baku dapat digunakan oleh beberapa menu.

### 4. Perhitungan Stok Menu Otomatis

Stok menu dihitung berdasarkan bahan baku yang tersedia.

Contoh:

| Bahan Baku | Stok |
|---|---:|
| Ayam | 3 |
| Nasi | 4 |
| Telur | 1 |

Jika Ayam Bakar dan Ayam Goreng masing-masing membutuhkan satu ayam dan satu nasi, maka kedua menu tersebut memiliki ketersediaan maksimal 3 porsi.

Ketika satu menu dipesan, stok bahan baku yang digunakan akan berkurang dan ketersediaan menu lain yang menggunakan bahan yang sama ikut diperbarui.

Rumus perhitungan:

Stok menu = Nilai minimum dari (Stok bahan baku / Kebutuhan bahan per menu).

### 5. Kasir dan Pencatatan Order

Digunakan oleh pegawai kasir untuk mencatat pesanan pelanggan.

Informasi transaksi meliputi:
- Nama pelanggan (opsional).
- Menu yang dipesan.
- Kuantitas setiap menu.
- Nomor antrean otomatis.
- Timestamp transaksi.
- Total harga pesanan.

Sistem akan menghitung total harga dan mengurangi bahan baku sesuai resep menu.

Transaksi tidak dapat diproses jika persediaan bahan baku tidak mencukupi.

### 6. Laporan Harian

Menyediakan rekap transaksi berdasarkan tanggal.

Informasi laporan:
- Tanggal transaksi.
- Jumlah pesanan.
- Total pendapatan harian.

Laporan dihitung berdasarkan data transaksi yang tersimpan di database.

---

## Alur Aplikasi

### A. Pengelolaan Menu

1. Pemilik memasukkan daftar menu makanan dan minuman.
2. Pemilik menentukan harga dan kategori menu.
3. Pemilik menentukan resep setiap menu.
4. Sistem menghubungkan menu dengan bahan baku yang dibutuhkan.
5. Sistem menghitung ketersediaan menu secara otomatis.

### B. Pengelolaan Bahan Baku

1. Pemilik memasukkan daftar bahan baku.
2. Pemilik menentukan satuan dan jumlah stok awal.
3. Ketika bahan baku dibeli, stok dapat ditambahkan.
4. Setiap perubahan stok dicatat dalam riwayat stok.
5. Sistem memperbarui ketersediaan menu.

### C. Proses Order

1. Kasir memilih menu yang dipesan pelanggan.
2. Kasir memasukkan kuantitas setiap menu.
3. Kasir dapat memasukkan nama pelanggan (opsional).
4. Sistem memeriksa ketersediaan bahan baku.
5. Sistem memberikan nomor antrean otomatis.
6. Sistem menghitung total harga pesanan.
7. Sistem menyimpan transaksi.
8. Sistem mengurangi stok bahan baku berdasarkan resep.
9. Sistem mencatat riwayat pengeluaran bahan baku.

### D. Laporan Harian

1. Sistem mengambil seluruh transaksi berdasarkan tanggal.
2. Sistem menghitung jumlah pesanan.
3. Sistem menjumlahkan pendapatan.
4. Sistem menampilkan laporan harian secara terstruktur.

---

## Database

Database yang digunakan adalah MySQL dengan rancangan tujuh tabel utama.

| No. | Nama Tabel | Fungsi |
|---|---|---|
| 1 | menu | Menyimpan data makanan dan minuman |
| 2 | bahan_baku | Menyimpan persediaan bahan baku |
| 3 | resep_menu | Menghubungkan menu dengan bahan baku |
| 4 | order | Menyimpan transaksi pelanggan |
| 5 | detail_order | Menyimpan rincian menu dalam transaksi |
| 6 | riwayat_stok | Menyimpan riwayat perubahan stok |
| 7 | laporan_harian | Menyimpan rekap pendapatan harian |

### Relasi Database

- Menu memiliki relasi One-to-Many dengan Resep_Menu.
- Bahan_Baku memiliki relasi One-to-Many dengan Resep_Menu.
- Menu dan Bahan_Baku memiliki relasi Many-to-Many melalui Resep_Menu.
- Order memiliki relasi One-to-Many dengan Detail_Order.
- Menu memiliki relasi One-to-Many dengan Detail_Order.
- Bahan_Baku memiliki relasi One-to-Many dengan Riwayat_Stok.
- Laporan_Harian merupakan rekap dari tabel Order berdasarkan tanggal.
