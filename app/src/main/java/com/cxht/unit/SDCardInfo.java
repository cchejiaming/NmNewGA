package com.cxht.unit;

//SDCardInfo�ࣺ

public class SDCardInfo
{
    /**
     * ����
     */
    private String label;


    /**
     * ���ص�
     */
    private String mountPoint;


    /**
     * �Ƿ��ѹ���
     */
    private boolean mounted;

    public SDCardInfo(){
    	
    }
    /**
     * @Description:��ȡSD������
     * @Date 2013-11-22
     * @return SD������
     */
    public String getLabel()
    {
        return label;
    }


    /**
     * @Description:����SD������
     * @Date 2013-11-22
     * @param label
     *            SD������
     */
    public void setLabel(String label)
    {
        this.label = label;
    }


    /**
     * @Description:��ȡ����·��
     * @Date 2013-11-22
     * @return ����·��
     */
    public String getMountPoint()
    {
        return mountPoint;
    }


    /**
     * @Description:���ù���·��
     * @Date 2013-11-22
     * @param mountPoint
     *            ����·��
     */
    public void setMountPoint(String mountPoint)
    {
        this.mountPoint = mountPoint;
    }


    /**
     * @Description:�Ƿ��Ѿ�����
     * @Date 2013-11-22
     * @return true:�Ѿ����أ�false:δ����
     */
    public boolean isMounted()
    {
        return mounted;
    }


    /**
     * @Description:�����Ƿ��Ѿ�����
     * @Date 2013-11-22
     * @param mounted
     *            true:�Ѿ����أ�false:δ����
     */
    public void setMounted(boolean mounted)
    {
        this.mounted = mounted;
    }


    @Override
    public String toString()
    {
        return "SDCardInfo [label=" + label + ", mountPoint=" + mountPoint
                + ", mounted=" + mounted + "]";
    }


}