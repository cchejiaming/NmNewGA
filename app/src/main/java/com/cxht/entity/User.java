package com.cxht.entity;

/**
 * @author hejiaming
 * �û�����ʵ����
 */
public class User {
    private int user_id;//�û����
    private int group_id; //����ID
    private String full_name; //���ڵ�λ
    private String number;//����
    private String user_name; //����
    private String sex; //�Ա�
    private String birth_date; //����
    private String user_code;//���֤��
    private String nativeplace;//����
    private String birthplace;//������
    private String nation;//����
    private String rd_time;//�˵�ʱ��
    private String politics_status;//������ò
    private String job_time;//����ʱ��
    private String status;//����״̬
    private String image;//��Ƭ
    private String photo; //��Ƭ2
    private String position; //ְ��
    private String depth; //ְ��
    private String before_education;//ȫ����ѧ��
    private String before_specialty;//ȫ����רҵ
    private String before_school;//ȫ����ѧУ
    private String before_degree;//ȫ����ѧλ
    private String before_time;//ȫ���Ʊ�ҵʱ��
    private String latest_education;//��ְѧ��
    private String latest_specialty;//��ְרҵ
    private String latest_school;//��ְѧУ
    private String latest_degree;//��ְѧλ
    private String latest_time;//��ְ��ҵʱ��
    private String highest_edu;//���ѧ��
    private String highest_degree;//���ѧλ
    private String specialty;//ר�����
    private String specialty_desc;//ר������
    private String job_title;//����ְ��
    private String spell;//ƴ����д
    private int sort;//����
    private String pre_job_time;//����Ҫְ����ְʱ��
    private String pre_depth_time;//��ְ��ʱ��
    private String rmb_path;//�����·��

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRd_time() {
        return rd_time;
    }

    public void setRd_time(String rd_time) {
        this.rd_time = rd_time;
    }

    public String getPolitics_status() {
        return politics_status;
    }

    public void setPolitics_status(String politics_status) {
        this.politics_status = politics_status;
    }

    public String getJob_time() {
        return job_time;
    }

    public void setJob_time(String job_time) {
        this.job_time = job_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getBefore_education() {
        return before_education;
    }

    public void setBefore_education(String before_education) {
        this.before_education = before_education;
    }

    public String getBefore_specialty() {
        return before_specialty;
    }

    public void setBefore_specialty(String before_specialty) {
        this.before_specialty = before_specialty;
    }

    public String getBefore_school() {
        return before_school;
    }

    public void setBefore_school(String before_school) {
        this.before_school = before_school;
    }

    public String getBefore_degree() {
        if (before_degree == null) before_degree = "";
        return before_degree;
    }

    public void setBefore_degree(String before_degree) {
        this.before_degree = before_degree;
    }

    public String getBefore_time() {
        return before_time;
    }

    public void setBefore_time(String before_time) {
        this.before_time = before_time;
    }

    public String getLatest_education() {
        return latest_education;
    }

    public void setLatest_education(String latest_education) {
        this.latest_education = latest_education;
    }

    public String getLatest_specialty() {
        return latest_specialty;
    }

    public void setLatest_specialty(String latest_specialty) {
        this.latest_specialty = latest_specialty;
    }

    public String getLatest_school() {
        return latest_school;
    }

    public void setLatest_school(String latest_school) {
        this.latest_school = latest_school;
    }

    public String getLatest_degree() {
        if (latest_degree == null) latest_degree = "";
        return latest_degree;
    }

    public void setLatest_degree(String latest_degree) {
        this.latest_degree = latest_degree;
    }

    public String getLatest_time() {
        return latest_time;
    }

    public void setLatest_time(String latest_time) {
        this.latest_time = latest_time;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialty_desc() {
        return specialty_desc;
    }

    public void setSpecialty_desc(String specialty_desc) {
        this.specialty_desc = specialty_desc;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getHighest_edu() {
        return highest_edu;
    }

    public void setHighest_edu(String highest_edu) {
        this.highest_edu = highest_edu;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHighest_degree() {
        return highest_degree;
    }

    public void setHighest_degree(String highest_degree) {
        this.highest_degree = highest_degree;
    }

    public String getRmb_path() {
        return rmb_path;
    }

    public void setRmb_path(String rmb_path) {
        this.rmb_path = rmb_path;
    }

    public String getPre_job_time() {
        return pre_job_time;
    }

    public void setPre_job_time(String pre_job_time) {
        this.pre_job_time = pre_job_time;
    }

    public String getPre_depth_time() {
        return pre_depth_time;
    }

    public void setPre_depth_time(String pre_depth_time) {
        this.pre_depth_time = pre_depth_time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
